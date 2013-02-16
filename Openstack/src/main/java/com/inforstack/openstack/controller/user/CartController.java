package com.inforstack.openstack.controller.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.drools.KnowledgeBase;
import org.drools.definition.type.FactType;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.inforstack.openstack.controller.model.CartItemModel;
import com.inforstack.openstack.controller.model.CartModel;
import com.inforstack.openstack.item.ItemService;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.rule.Rule;
import com.inforstack.openstack.rule.RuleService;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.tenant.TenantService;
import com.inforstack.openstack.user.User;
import com.inforstack.openstack.user.UserService;
import com.inforstack.openstack.utils.JSONUtil;
import com.inforstack.openstack.utils.RuleUtils;
import com.inforstack.openstack.utils.SecurityUtils;

@Controller
@RequestMapping(value = "/user/cart")
public class CartController {

	private static final Logger log = new Logger(CartController.class);

	public static final String CART_SESSION_ATTRIBUTE_NAME = CartController.class
			.getName() + ".Cart";

	@Autowired
	private UserService userService;

	@Autowired
	private TenantService tenantService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private RuleService ruleService;

	private final String CART_MODULE_HOME = "user/modules/Cart";

	@RequestMapping(value = "/modules/index", method = RequestMethod.GET)
	public String redirectModule(Model model, HttpServletRequest request) {
		return CART_MODULE_HOME + "/index";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> create(HttpServletRequest request, Model model) {
		CartModel cart = null;
		Object sessionAttribute = WebUtils.getSessionAttribute(request,
				CART_SESSION_ATTRIBUTE_NAME);
		if (sessionAttribute == null) {
			cart = new CartModel();
			cart.setItems(new CartItemModel[0]);
			cart.setAmount(0f);
		} else {
			cart = (CartModel) sessionAttribute;
		}
		WebUtils.setSessionAttribute(request, CART_SESSION_ATTRIBUTE_NAME, cart);
		return JSONUtil.jsonSuccess(cart);
	}

	@RequestMapping(value = "/clear", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> clear(HttpServletRequest request, Model model) {
		CartModel cart = new CartModel();
		cart.setItems(new CartItemModel[0]);
		cart.setAmount(0f);
		WebUtils.setSessionAttribute(request, CART_SESSION_ATTRIBUTE_NAME, cart);
		return JSONUtil.jsonSuccess(cart);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> add(HttpServletRequest request, Model model,
			@RequestBody CartItemModel cartItem) {
		if (cartItem != null) {
			cartItem.setUuid(UUID.randomUUID().toString());
			cartItem.setStatus(0);

			CartModel cart = null;
			Object sessionAttribute = WebUtils.getSessionAttribute(request,
					CART_SESSION_ATTRIBUTE_NAME);
			if (sessionAttribute == null) {
				cart = new CartModel();
				cart.setItems(new CartItemModel[1]);
				cart.getItems()[0] = cartItem;
			} else {
				cart = (CartModel) sessionAttribute;
				CartItemModel[] oldItems = cart.getItems();
				CartItemModel[] items = Arrays.copyOf(oldItems,
						oldItems.length + 1);
				items[oldItems.length] = cartItem;
				cart.setItems(items);
			}

			// this.runRules(cart);

			float amount = 0;
			CartItemModel[] items = cart.getItems();
			for (CartItemModel item : items) {
				amount += (item.getNumber().intValue() * item.getPrice()
						.floatValue());
			}
			cart.setAmount(amount);
			WebUtils.setSessionAttribute(request, CART_SESSION_ATTRIBUTE_NAME,
					cart);
			return JSONUtil.jsonSuccess(cart);
		}

		return JSONUtil.jsonError(null);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> update(HttpServletRequest request, Model model,
			@RequestBody CartItemModel cartItem) {

		if (cartItem != null) {
			CartItemModel existItem = null;
			CartModel cart = null;
			Object sessionAttribute = WebUtils.getSessionAttribute(request,
					CART_SESSION_ATTRIBUTE_NAME);
			if (sessionAttribute != null) {
				cart = (CartModel) sessionAttribute;
				CartItemModel[] items = cart.getItems();
				for (CartItemModel item : items) {
					if (item.getUuid().equalsIgnoreCase(cartItem.getUuid())) {
						existItem = item;
						break;
					}
				}
			}

			if (existItem != null) {
				existItem.setName(cartItem.getName());
				existItem.setItemSpecificationId(cartItem
						.getItemSpecificationId());
				existItem.setNumber(cartItem.getNumber());
				existItem.setPrice(cartItem.getPrice());
				existItem.setStatus(0);

				this.runRules(cart);

				float amount = 0;
				CartItemModel[] items = cart.getItems();
				for (CartItemModel item : items) {
					amount += (item.getNumber().intValue() * item.getPrice()
							.floatValue());
				}
				cart.setAmount(amount);
				WebUtils.setSessionAttribute(request,
						CART_SESSION_ATTRIBUTE_NAME, cart);
				return JSONUtil.jsonSuccess(cart);
			}
		}

		return JSONUtil.jsonError(null);
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> remove(HttpServletRequest request, Model model,
			@RequestBody CartItemModel cartItem) {

		if (cartItem != null) {
			CartModel cart = null;
			Object sessionAttribute = WebUtils.getSessionAttribute(request,
					CART_SESSION_ATTRIBUTE_NAME);
			if (sessionAttribute != null) {
				cart = (CartModel) sessionAttribute;
				CartItemModel[] items = cart.getItems();
				ArrayList<CartItemModel> itemList = new ArrayList<CartItemModel>();
				for (CartItemModel item : items) {
					if (!item.getUuid().equalsIgnoreCase(cartItem.getUuid())) {
						itemList.add(item);
					}
				}
				cart.setItems(itemList.toArray(new CartItemModel[0]));

				// this.runRules(cart);

				float amount = 0;
				items = cart.getItems();
				for (CartItemModel item : items) {
					amount += (item.getNumber().intValue() * item.getPrice()
							.floatValue());
				}
				cart.setAmount(amount);
				WebUtils.setSessionAttribute(request,
						CART_SESSION_ATTRIBUTE_NAME, cart);
				return JSONUtil.jsonSuccess(cart);
			}
		}

		return JSONUtil.jsonError(null);
	}

	private void runRules(CartModel cart) {
		User user = this.userService.findByName(SecurityUtils.getUserName());
		Tenant tenant = null;
		if (user != null) {
			tenant = this.tenantService.findTenantById(SecurityUtils
					.getTenant().getId());
		}

		List<Rule> ruleList = this.ruleService.listRuleByTypeName("promotion");
		for (Rule rule : ruleList) {
			try {
				KnowledgeBase kbase = RuleUtils.readKnowledgeBase(
						rule.getName(), rule.getLocationType(),
						rule.getLocation());
				FactType userType = kbase.getFactType("troposphere", "User");
				Object userFact = userType.newInstance();
				StatefulKnowledgeSession ksession = kbase
						.newStatefulKnowledgeSession();
				ksession.insert(userFact);
				ksession.fireAllRules();
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			}
		}
	}
}
