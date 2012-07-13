package com.heroku;

import com.atlassian.bamboo.collections.ActionParametersMap;
import com.atlassian.bamboo.security.EncryptionException;
import com.atlassian.bamboo.security.StringEncrypter;
import com.atlassian.bamboo.task.AbstractTaskConfigurator;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.user.BambooAuthenticationContext;
import com.atlassian.bamboo.utils.error.ErrorCollection;
import com.atlassian.bamboo.ww2.actions.build.admin.config.task.ConfigureBuildTasks;
import com.google.common.collect.ImmutableList;
import com.opensymphony.xwork.TextProvider;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class AbstractDeploymentTaskConfigurator extends AbstractTaskConfigurator implements DeploymentPipeline {

    static final String API_KEY = "apiKey";
    private static final String APP_NAME = "appName";

    private TextProvider textProvider;

    protected List<String> getFieldsToCopy() {
        return ImmutableList.<String>builder().add(API_KEY, APP_NAME).addAll(getRequiredFiles()).build();
    }

    @NotNull
    @Override
    public Map<String, String> generateTaskConfigMap(@NotNull final ActionParametersMap params, @Nullable final TaskDefinition previousTaskDefinition) {
        final Map<String, String> config = super.generateTaskConfigMap(params, previousTaskDefinition);
        taskConfiguratorHelper.populateTaskConfigMapWithActionParameters(config, params, getFieldsToCopy());
        return config;
    }

    @Override
    public void populateContextForCreate(@NotNull final Map<String, Object> context) {
        super.populateContextForCreate(context);
    }

    @Override
    public void populateContextForEdit(@NotNull final Map<String, Object> context, @NotNull final TaskDefinition taskDefinition) {
        super.populateContextForEdit(context, taskDefinition);
        taskConfiguratorHelper.populateContextWithConfiguration(context, taskDefinition, getFieldsToCopy());
    }

    @Override
    public void populateContextForView(@NotNull final Map<String, Object> context, @NotNull final TaskDefinition taskDefinition) {
        super.populateContextForView(context, taskDefinition);
        taskConfiguratorHelper.populateContextWithConfiguration(context, taskDefinition, getFieldsToCopy());
    }

    @Override
    public void validate(@NotNull final ActionParametersMap params, @NotNull final ErrorCollection errorCollection) {
        super.validate(params, errorCollection);
        
        for (String field : getFieldsToCopy()) {
            if (StringUtils.isEmpty(params.getString(field))) {
                errorCollection.addError(field, "Required");
            }
        }

        if (params.containsKey(API_KEY)) {
            if (!(params.get(API_KEY) instanceof String[])) { throw new RuntimeException("Unexpected API_KEY format"); }
            final String[] apiKeyArray = (String[]) params.get(API_KEY);
            if (apiKeyArray.length != 1) { throw new RuntimeException("Unexpected API_KEY array length"); }
            final String apiKey = apiKeyArray[0];

            final StringEncrypter stringEncrypter = new StringEncrypter();
            try {
                // test if the key is already encrypted
                stringEncrypter.decrypt(apiKey);
            } catch (EncryptionException e) {
                // otherwise, encrypt it
                params.put(API_KEY, new String[]{stringEncrypter.encrypt(apiKey)});
            }
        }
    }

    public void setTextProvider(final TextProvider textProvider) {
        this.textProvider = textProvider;
    }
}
