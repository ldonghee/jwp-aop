package core.di.beans.factory.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.di.beans.factory.FactoryBean;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class ProxyFactoryBean implements FactoryBean<Object> {

	private static final Logger logger = LoggerFactory.getLogger(ProxyFactoryBean.class);
	private Class<?> target;
	private Advice advice;
	private Pointcut pointcut;

	public void setTarget(Class<?> target) {
		this.target = target;
	}

	public void setAdvice(Advice advice) {
		this.advice = advice;
	}

	public void setPointcut(Pointcut pointcut) {
		this.pointcut = pointcut;
	}

	@Override
	public Object getObject() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target);
		enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
			if (pointcut.matches(method)) {
				return advice.intercept(obj, method, args, proxy);
			}
			return proxy.invokeSuper(obj, args);
		});
		return enhancer.create();
	}


	@Override
	public Class<?> getObjectType() {
		return target.getClass();
	}
}
