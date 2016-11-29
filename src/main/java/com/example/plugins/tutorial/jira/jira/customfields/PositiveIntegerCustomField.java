package com.example.plugins.tutorial.jira.jira.customfields;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.atlassian.jira.issue.customfields.impl.AbstractSingleFieldType;
import com.atlassian.jira.issue.customfields.impl.FieldValidationException;
import com.atlassian.jira.issue.customfields.manager.GenericConfigManager;
import com.atlassian.jira.issue.customfields.persistence.CustomFieldValuePersister;
import com.atlassian.jira.issue.customfields.persistence.PersistenceFieldType;

public class PositiveIntegerCustomField extends AbstractSingleFieldType<Integer> {
    private static final Logger log = LoggerFactory.getLogger(PositiveIntegerCustomField.class);

    public PositiveIntegerCustomField(CustomFieldValuePersister customFieldValuePersister,
            GenericConfigManager genericConfigManager) {
        super(customFieldValuePersister, genericConfigManager);
    }

    @Override
    protected PersistenceFieldType getDatabaseType()
    {
        return PersistenceFieldType.TYPE_LIMITED_TEXT;
    }

    @Override
    protected Object getDbValueFromObject(final Integer customFieldObject)
    {
        return getStringFromSingularObject(customFieldObject);
    }

    @Override
    protected Integer getObjectFromDbValue(final Object databaseValue)
            throws FieldValidationException
    {
        return getSingularObjectFromString((String) databaseValue);
    }

    @Override
    public String getStringFromSingularObject(final Integer singularObject)
    {
        if (singularObject == null)
            return "";
        return singularObject.toString();
    }

    @Override
    public Integer getSingularObjectFromString(final String string)
            throws FieldValidationException
    {
        if (string == null)
            return null;
        try
        {
            final Integer i = new Integer(string);
            if (i < 0)
            {
                throw new FieldValidationException(
                        "Only positive integer allowed");
            }
            return i;
            
        }
        catch (NumberFormatException ex)
        {
            throw new FieldValidationException("Not a valid number.");
        }
    }
}