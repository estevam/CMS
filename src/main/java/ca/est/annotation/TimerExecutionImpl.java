package ca.est.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
*
* @author Estevam.Meneses
* @since 05/10/2019
* @apiNote 
* @category
*/
@Aspect
@Component
public class TimerExecutionImpl {
    
	final static Logger log = LoggerFactory.getLogger(TimerExecutionImpl.class);
	
	@Around("@annotation(ca.est.annotation.TimerExecution) && execution(public * *(..))")
    public Object time(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    	
        long start = System.currentTimeMillis();

        Object value;

        try {
            value = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            long duration = System.currentTimeMillis() - start;
            log.info(
                    "{}.{} took {} ms",
                    proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName(),
                    proceedingJoinPoint.getSignature().getName(),
                    duration);
        }

        return value;
    }
}
