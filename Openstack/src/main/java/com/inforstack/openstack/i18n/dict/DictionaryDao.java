package com.inforstack.openstack.i18n.dict;

import java.util.List;

public interface DictionaryDao {

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

}
