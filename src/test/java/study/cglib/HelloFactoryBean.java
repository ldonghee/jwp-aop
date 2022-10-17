package study.cglib;

import core.di.beans.factory.FactoryBean;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import study.dynamicProxy.Hello;
import study.dynamicProxy.HelloTarget;

public class HelloFactoryBean implements FactoryBean<Hello> {
	@Override
	public HelloTarget getObject() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(HelloTarget.class);
		enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
			if (method.getName().startsWith("say")) {
				return proxy.invokeSuper(obj, args).toString().toUpperCase();
			}
			return proxy.invokeSuper(obj, args);
		});

		return (HelloTarget) enhancer.create();
	}

	@Override
	public Class<HelloTarget> getObjectType() {
		return HelloTarget.class;
	}
}
