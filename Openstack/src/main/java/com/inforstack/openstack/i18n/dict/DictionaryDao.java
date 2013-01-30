package com.inforstack.openstack.i18n.dict;

import java.util.List;

import com.inforstack.openstack.utils.db.IDao;

public interface DictionaryDao extends IDao<Dictionary> {

	/**
	 * 
	 * @param key
	 * @param languageId
	 * @return null if no record found
	 */
	public List<Dictionary> findByKeyAndLanguage(String key, Integer languageId);

	/**
	 * 
	 * @param key
	 * @param languageId
	 * @param code
	 * @return return if not found
	 */
	public Dictionary findDictionary(String key, Integer languageId, String code);

	/**
	 * find dictionary with all language
	 * @param key
	 * @param code
	 * @return null if not found
	 */
	public List<Dictionary> findDictionary(String key, String code);

}
