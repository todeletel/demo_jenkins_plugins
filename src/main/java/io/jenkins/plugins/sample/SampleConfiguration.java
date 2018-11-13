package io.jenkins.plugins.sample;

import hudson.Extension;
import hudson.util.FormValidation;
import io.jenkins.plugins.sample.utils.Auth;
import jenkins.model.GlobalConfiguration;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;

/**
 * Example of Jenkins global configuration.
 */
@Extension
public class SampleConfiguration extends GlobalConfiguration {

    /** @return the singleton instance */
    public static SampleConfiguration get() {
        return GlobalConfiguration.all().get(SampleConfiguration.class);
    }

    private String label;
    private String user;
    private String password;
    private String timeout;

    public SampleConfiguration() {
        // When Jenkins is restarted, load any saved configuration from disk.
        load();
    }

    /** @return the currently configured label, if any */
    public String getLabel() {
        return label;
    }

    public String getUser() {
        return user;
    }

    @DataBoundSetter
    public void setUser(String user) {
        this.user = user;
        save();

    }

    public String getPassword() {
        return password;
    }

    @DataBoundSetter
    public void setPassword(String password) {
        this.password = password;
        save();

    }

    public String getTimeout() {
        return timeout;
    }

    @DataBoundSetter
    public void setTimeout(String timeout) {
        this.timeout = timeout;
        save();
    }

    /**
     * Together with {@link #getLabel}, binds to entry in {@code config.jelly}.
     * @param label the new value of this field
     */
    @DataBoundSetter
    public void setLabel(String label) {
        this.label = label;
        save();
    }

    public FormValidation doCheckLabel(@QueryParameter String value) {
        if (StringUtils.isEmpty(value)) {
            return FormValidation.warning("Please specify a label.");
        }
        return FormValidation.ok();
    }

    public FormValidation doTestConnection(@QueryParameter String user,
                                           @QueryParameter String password) {
        String oks = user + password;
        Auth auth = new Auth();
        boolean result = false;
        try {
            result = auth.auth(user,password);

        }catch (Exception e){
            return FormValidation.error(e.toString());
        }
        String reuslt_str="";
        if(result){
            reuslt_str = "成功:";

        }else{
            reuslt_str = "失败:";
        }

        return FormValidation.ok(reuslt_str + oks);

    }

}
