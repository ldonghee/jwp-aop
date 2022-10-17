package core.di.beans.factory.proxy;

import java.lang.reflect.Method;

public interface Pointcut {
	boolean matches(Method method);
}
