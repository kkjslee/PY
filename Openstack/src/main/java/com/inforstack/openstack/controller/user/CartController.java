package com.inforstack.openstack.controller.user;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.drools.KnowledgeBase;
import org.drools.definition.type.FactType;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.api.nova.flavor.Flavor;
import com.inforstack.openstack.api.nova.flavor.FlavorService;
import com.inforstack.openstack.api.nova.image.Image;
import com.inforstack.openstack.api.nova.image.ImageService;
import com.inforstack.openstack.controller.model.CartItemModel;
import com.inforstack.openstack.controller.model.CartModel;
import com.inforstack.openstack.controller.model.CategoryModel;
import com.inforstack.openstack.controller.model.FlavorModel;
import com.inforstack.openstack.controller.model.I18nModel;
import com.inforstack.openstack.controller.model.ImageModel;
import com.inforstack.openstack.controller.model.ItemSpecificationModel;
import com.inforstack.openstack.controller.model.ProfileModel;
import com.inforstack.openstack.i18n.I18n;
import com.inforstack.openstack.i18n.I18nService;
import com.inforstack.openstack.item.Category;
import com.inforstack.openstack.item.ItemService;
import com.inforstack.openstack.item.ItemSpecification;
import com.inforstack.openstack.item.Profile;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.order.Order;
import com.inforstack.openstack.order.OrderService;
import com.inforstack.openstack.rule.Rule;
import com.inforstack.openstack.rule.RuleService;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.tenant.TenantService;
import com.inforstack.openstack.user.User;
import com.inforstack.openstack.user.UserService;
import com.inforstack.openstack.utils.JSONUtil;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.RuleUtils;
import com.inforstack.openstack.utils.SecurityUtils;
import com.inforstack.openstack.utils.ValidateUtil;

@Controller
@RequestMapping(value = "/user/cart")
public class CartController {

	private static final Logger log = new Logger(CartController.class);

	public static final String CART_SESSION_ATTRIBUTE_NAME = CartController.class
			.getName() + ".Cart";

	@Autowired
	private UserService userService;

	@Autowired
	private FlavorService flavorService;

	@Autowired
	private I18nService i18nService;

	@Autowired
	private TenantService tenantService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private KeystoneService keystoneService;

	@Autowired
	private RuleService ruleService;

	@Autowired
	private OrderService orderService;

	private final String CART_MODULE_HOME = "user/modules/Cart";

	@RequestMapping(value = "/modules/index", method = RequestMethod.GET)
	public String redirectModule(Model model, HttpServletRequest request) {
		clear(request, model);
		List<ItemSpecificationModel> imgList = new ArrayList<ItemSpecificationModel>();
		imgList = listProductsForUser(ItemSpecification.OS_TYPE_IMAGE_ID);
		model.addAttribute("imgList", imgList);

		List<ItemSpecificationModel> flavorModels = new ArrayList<ItemSpecificationModel>();
		flavorModels = listProductsForUser(ItemSpecification.OS_TYPE_FLAVOR_ID);
		model.addAttribute("flavorList", flavorModels);
		return CART_MODULE_HOME + "/index";
	}

	@RequestMapping(value = "/scripts/bootstrap", method = RequestMethod.GET)
	public String bootstrap(Model model) {
		return CART_MODULE_HOME + "/scripts/bootstrap";
	}

	@RequestMapping(value = "/scripts/template", method = RequestMethod.GET)
	public String template(Model model) {
		return CART_MODULE_HOME + "/scripts/template";
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
			CartItemModel cartItem) {
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
			cart.setCurrentItemUUID(cartItem.getUuid());
			WebUtils.setSessionAttribute(request, CART_SESSION_ATTRIBUTE_NAME,
					cart);
			return JSONUtil.jsonSuccess(cart);
		}

		return JSONUtil.jsonError(null);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> update(HttpServletRequest request, Model model,
			CartItemModel cartItem) {

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

				/* this.runRules(cart); */

				float amount = 0;
				CartItemModel[] items = cart.getItems();
				for (CartItemModel item : items) {
					amount += (item.getNumber().intValue() * item.getPrice()
							.floatValue());
				}
				cart.setAmount(amount);
				cart.setCurrentItemUUID(cartItem.getUuid());
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
			CartItemModel cartItem) {

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
				cart.setCurrentItemUUID(null);
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

	private List<ImageModel> getImages(boolean needCheck) {
		List<ImageModel> imgList = new ArrayList<ImageModel>();
		try {
			Image[] images = imageService.listImages();
			if (images != null) {
				ImageModel imgModel = null;
				for (Image img : images) {
					imgModel = new ImageModel();
					imgModel.setImgId(img.getId());
					imgModel.setImgName(img.getName());
					imgModel.setCreated(img.getCreated());
					imgModel.setMinDisk(img.getMinDisk());
					imgModel.setMinRam(img.getMinRam());
					imgModel.setProgress(img.getProgress());
					imgModel.setStatus(img.getStatus());
					imgModel.setTenant(img.getTenant());
					imgModel.setUpdated(img.getUpdated());
					imgModel.setUser(img.getUser());
					if (needCheck) {
						if (!ValidateUtil.checkValidImg(img)) {
							continue;
						}
					}
					imgList.add(imgModel);
				}
			}
		} catch (OpenstackAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imgList;
	}

	private List<FlavorModel> getAllFlavors(boolean needCheck) {
		List<FlavorModel> flavorModels = new ArrayList<FlavorModel>();
		try {
			Flavor[] flavors = flavorService.listFlavors();
			FlavorModel flavorModel = null;
			for (Flavor flavor : flavors) {
				flavorModel = new FlavorModel();
				flavorModel.setFlavorId(flavor.getId());
				flavorModel.setDisk(flavor.getDisk());
				flavorModel.setFlavorName(flavor.getName());
				flavorModel.setRam(flavor.getRam());
				flavorModel.setVcpus(flavor.getVcpus());
				if (needCheck) {
					if (!ValidateUtil.checkValidFlavor(flavor)) {
						continue;
					}
				}
				flavorModels.add(flavorModel);
			}
		} catch (OpenstackAPIException e) {
			e.printStackTrace();
		}
		return flavorModels;
	}

	private List<ItemSpecificationModel> listProductsForUser(int osType) {
		Integer languageId = OpenstackUtil.getLanguage().getId();
		List<ItemSpecificationModel> models = new ArrayList<ItemSpecificationModel>();

		List<ItemSpecification> itemSpecifications = this.itemService
				.listAllItemSpecification();

		for (ItemSpecification itemSpecification : itemSpecifications) {
			if (itemSpecification.getAvailable() == true
					&& itemSpecification.getOsType() == osType) {
				I18nModel[] itemName = new I18nModel[1];
				itemName[0] = new I18nModel();
				itemName[0].setLanguageId(languageId);
				itemName[0].setContent(itemSpecification.getName()
						.getI18nContent());

				ItemSpecificationModel itemSpecificationModel = new ItemSpecificationModel();
				itemSpecificationModel.setId(itemSpecification.getId());
				itemSpecificationModel.setName(itemName);
				itemSpecificationModel.setOsType(itemSpecification.getOsType());
				itemSpecificationModel.setRefId(itemSpecification.getRefId());

				if (osType == ItemSpecification.OS_TYPE_FLAVOR_ID
						|| osType == ItemSpecification.OS_TYPE_VOLUME_ID
						|| osType == ItemSpecification.OS_TYPE_IMAGE_ID) {
					Map<String, String> details = itemService
							.getItemSpecificationDetail(itemSpecification
									.getId());
					Map<String, String> i18Details = new HashMap<String, String>();
					Iterator<Entry<String, String>> it = details.entrySet()
							.iterator();
					while (it.hasNext()) {
						Entry<String, String> entry = (Entry<String, String>) it
								.next();
						String key = entry.getKey();
						String value = entry.getValue();
						if (key.equals("os_cpu")) {
							i18Details.put(OpenstackUtil
									.getMessage("admin.flavor.vcpus"), value);
						}
						if (key.equals("os_memory")) {
							i18Details.put(OpenstackUtil
									.getMessage("admin.flavor.ram"), value);
						}
						if (key.equals("os_disk")) {
							i18Details.put(OpenstackUtil
									.getMessage("admin.flavor.rdisk"), value);
						}
						if (key.equals("os_size")) {
							i18Details.put(
									OpenstackUtil.getMessage("volume.size"),
									value);
						}
						if (key.equals("os_imagename")) {
							i18Details.put(OpenstackUtil
									.getMessage("admin.image.name"), value);
						}
					}
					itemSpecificationModel.setDetails(i18Details);
				}

				List<Category> cList = itemSpecification.getCategories();
				CategoryModel[] cModelArray = new CategoryModel[cList.size()];
				for (int i = 0; i < cList.size(); i++) {
					Category c = cList.get(i);
					CategoryModel cModel = new CategoryModel();
					cModel.setEnable(c.getEnable());
					cModel.setSystem(c.getSystem());
					cModel.setId(c.getId());
					I18nModel[] cName = new I18nModel[1];
					cName[0] = new I18nModel();
					cName[0].setLanguageId(languageId);

					int cNameId = c.getNameId();
					if (cNameId > 0) {
						log.info("item name ID:" + cNameId);
						I18n i18n = null;
						i18n = i18nService.findByLinkAndLanguage(cNameId,
								languageId);
						if (i18n != null) {
							cName[0].setContent(i18n.getContent());
						}

					} else {
						log.warn("category name id is null ,item id:"
								+ c.getId());
					}

					cModel.setName(cName);
					cModelArray[i] = cModel;
				}
				itemSpecificationModel.setCategories(cModelArray);
				itemSpecificationModel.setDefaultPrice(itemSpecification
						.getDefaultPrice());
				itemSpecificationModel.setAvailable(itemSpecification
						.getAvailable());
				itemSpecificationModel.setCreated(DateFormat
						.getDateTimeInstance(DateFormat.SHORT,
								DateFormat.SHORT, OpenstackUtil.getLocale())
						.format(itemSpecification.getCreated()));
				itemSpecificationModel.setUpdated(DateFormat
						.getDateTimeInstance(DateFormat.SHORT,
								DateFormat.SHORT, OpenstackUtil.getLocale())
						.format(itemSpecification.getUpdated()));

				Profile profile = itemSpecification.getProfile();
				if (profile != null) {
					ProfileModel profileModel = new ProfileModel();
					profileModel.setItem(itemSpecification.getId());
					if (profile.getCpu() != null) {
						profileModel.setCpu(profile.getCpu().getId());
					}
					if (profile.getMemory() != null) {
						profileModel.setMemory(profile.getMemory().getId());
					}
					if (profile.getDisk() != null) {
						profileModel.setDisk(profile.getDisk().getId());
					}
					if (profile.getNetwork() != null) {
						profileModel.setNetwork(profile.getNetwork().getId());
					}

					itemSpecificationModel.setProfile(profileModel);
				}
				models.add(itemSpecificationModel);
			}
		}
		return models;
	}

	@RequestMapping(value = "/checkout", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> checkout(Model model, HttpServletRequest request) {
		Object sessionAttribute = WebUtils.getSessionAttribute(request,
				CART_SESSION_ATTRIBUTE_NAME);
		if (sessionAttribute == null
				|| (sessionAttribute instanceof CartModel) == false) {
			return OpenstackUtil.buildErrorResponse(OpenstackUtil
					.getMessage("cart.empty"));
		}

		CartModel cartModel = (CartModel) sessionAttribute;
		if (cartModel.getItems() == null || cartModel.getItems().length == 0) {
			return OpenstackUtil.buildErrorResponse(OpenstackUtil
					.getMessage("cart.empty"));
		}

		Order order = null;
		try {
			order = orderService.createOrder(cartModel);
		} catch (RuntimeException re) {
			log.error("create order failed", re);
		}

		if (order == null) {
			return OpenstackUtil.buildErrorResponse(OpenstackUtil
					.getMessage("cart.checkout.failed"));
		} else {
			WebUtils.setSessionAttribute(request, CART_SESSION_ATTRIBUTE_NAME, null);
			return OpenstackUtil.buildSuccessResponse(order.getId());
		}
	}

	@RequestMapping(value = "/showPayMethods", method = RequestMethod.POST)
	public String showPayMethod(Model model, HttpServletRequest request,
			Integer orderId) {
		model.addAttribute("orderId", orderId);
		return CART_MODULE_HOME + "/payMethods";

	}

}
