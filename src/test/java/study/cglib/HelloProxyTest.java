package study.cglib;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;
import org.junit.jupiter.api.Test;

class HelloProxyTest {
    @Test
    void toUppercase(){
        // 1. Enhancer 객체를 생성
        Enhancer enhancer = new Enhancer();
        // 2. setSuperclass() 메소드에 프록시할 클래스 지정
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                return proxy.invokeSuper(obj, args).toString().toUpperCase();
            }
        });
        
        // 3. enhancer.create()로 프록시 생성
        Object obj = enhancer.create();
        // 4. 프록시를 통해서 간접 접근
        HelloTarget proxiedHello =(HelloTarget)obj;

        assertThat(proxiedHello.sayHello("javajigi")).isEqualTo("HELLO JAVAJIGI");
        assertThat(proxiedHello.sayHi("javajigi")).isEqualTo("HI JAVAJIGI");
        assertThat(proxiedHello.sayThankYou("javajigi")).isEqualTo("THANK YOU JAVAJIGI");
    }

}
