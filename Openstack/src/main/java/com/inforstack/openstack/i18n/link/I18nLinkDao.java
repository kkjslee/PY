package com.inforstack.openstack.i18n.link;

public interface I18nLinkDao {

	public void persist(I18nLink link);

	public I18nLink findById(Integer linkId);

	public void remove(I18nLink link);

}
