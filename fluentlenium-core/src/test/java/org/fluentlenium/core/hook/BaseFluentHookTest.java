package org.fluentlenium.core.hook;

import com.google.common.base.Suppliers;
import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.annotation.Annotation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BaseFluentHookTest {
    @Mock
    private WebDriver webDriver;

    @Mock
    private WebElement element;

    @Mock
    private ElementLocator locator;

    @Mock
    private Object options;

    private DefaultComponentInstantiator instantiator;

    private BaseFluentHook<?> hook;

    private FluentAdapter fluentAdapter;

    @Before
    public void before() {
        fluentAdapter = new FluentAdapter(webDriver);
        instantiator = spy(new DefaultComponentInstantiator(fluentAdapter));
        hook = new BaseFluentHook<>(fluentAdapter, instantiator, Suppliers.ofInstance(element),  Suppliers.ofInstance(locator), options);
    }

    @Test
    public void testFluentWebElement() {
        FluentWebElement fluentWebElement = hook.getFluentWebElement();
        verify(instantiator).newComponent(FluentWebElement.class, element);

        assertThat(fluentWebElement).isInstanceOf(FluentWebElement.class);
        assertThat(fluentWebElement.getElement()).isSameAs(element);
    }

}