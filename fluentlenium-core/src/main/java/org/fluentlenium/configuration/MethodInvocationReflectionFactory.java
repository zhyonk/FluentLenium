package org.fluentlenium.configuration;

import org.openqa.selenium.Capabilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@IndexIgnore
public class MethodInvocationReflectionFactory implements CapabilitiesFactory, FactoryNames {
    private final Method method;
    private final Object instance;
    private final Object[] args;

    public MethodInvocationReflectionFactory(final Method method, final Object instance, final Object... args) {
        this.method = method;
        this.instance = instance;
        this.args = args;
    }

    @Override
    public Capabilities newCapabilities(final ConfigurationProperties configuration) {
        try {
            return (Capabilities) this.method.invoke(instance, args);
        } catch (final IllegalAccessException e) {
            throw new ConfigurationException("Can't create capabilities instance", e);
        } catch (final InvocationTargetException e) {
            throw new ConfigurationException("Can't create capabilities instance", e);
        }
    }

    @Override
    public String[] getNames() {
        return new String[] {this.method.getDeclaringClass().getName() + "." + this.method.getName(),
                this.method.getDeclaringClass().getSimpleName() + "." + this.method.getName(), this.method.getName()};
    }
}
