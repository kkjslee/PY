package com.inforstack.openstack.i18n.dict;

import java.util.List;

public interface DictionaryService {
	
	public List<Dictionary> findDictsByKeyAndLanguageId(String key, Integer languageId);
	
	public Dictionary findDict(String key, Integer languageId, String code);
	
	public boolean contains(String key, String code);

}
