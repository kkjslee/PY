import com.infosense.ibilling.server.util.PreferenceBL
import org.springframework.dao.EmptyResultDataAccessException
import org.hibernate.ObjectNotFoundException

class PreferencesTagLib {

    /**
     * Prints the preference value
     *
     * @param preferenceId ID of the preference to check
     */
    def preference = { attrs, body ->

        def preferenceId = assertAttribute('preferenceId', attrs, 'hasPreference') as Integer

        try {
            PreferenceBL preference = new PreferenceBL(session['company_id'], preferenceId);
            if (!preference.isNull())
                out << preference.getValueAsString()

        } catch (EmptyResultDataAccessException e) {
            /* ignore */
        } catch (ObjectNotFoundException e) {
            /* ignore */
        }
    }

    /**
     * Prints the tag body if the preference exists and is not null.
     *
     * @param preferenceId ID of the preference to check
     */
    def hasPreference = { attrs, body ->

        def preferenceId = assertAttribute('preferenceId', attrs, 'hasPreference') as Integer

        try {
            PreferenceBL preference = new PreferenceBL(session['company_id'], preferenceId);
            if (!preference.isNull())
                out << body()

        } catch (EmptyResultDataAccessException e) {
            /* ignore */
        } catch (ObjectNotFoundException e) {
            /* ignore */
        }
    }

    /**
     * Prints the tag body if the preference value or preference type default equals the given value.
     *
     * @param preferenceId ID of the preference to check
     * @param value to compare
     */
    def preferenceEquals = { attrs, body ->

        def preferenceId = assertAttribute('preferenceId', attrs, 'preferenceEquals') as Integer
        def value = assertAttribute('value', attrs, 'preferenceEquals') as String

        try {
            PreferenceBL preference = new PreferenceBL(session['company_id'], preferenceId)
            if (preference.getValueAsString().equals(value))
                out << body()

        } catch (EmptyResultDataAccessException e) {
            /* ignore */
            log.debug("empty result data access exception")

        } catch (ObjectNotFoundException e) {
            /* ignore */
            log.debug("object not found exception")
        }
    }

    /**
     * Prints the tag body if the preference value is equal, or if the preference is not set and has no
     * default value for the preference type. Useful for "default if not set" style preferences.
     *
     * @param preferenceId ID of the preference to check
     * @param value to compare
     */
    def preferenceIsNullOrEquals = { attrs, body ->

        def preferenceId = assertAttribute('preferenceId', attrs, 'preferenceEquals') as Integer
        def value = assertAttribute('value', attrs, 'preferenceEquals') as String

        try {
            PreferenceBL preference = new PreferenceBL(session['company_id'], preferenceId)
            if (!preference.isNull() && !preference.getValueAsString().equals(value))
                return

        } catch (EmptyResultDataAccessException e) {
            /* ignore */
        } catch (ObjectNotFoundException e) {
            /* ignore */
        }

        out << body()
    }

    protected assertAttribute(String name, attrs, String tag) {
        if (!attrs.containsKey(name)) {
            throwTagError "Tag [$tag] is missing required attribute [$name]"
        }
        attrs.remove name
    }
}
