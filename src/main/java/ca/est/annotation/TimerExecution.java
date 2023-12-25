package ca.est.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
*
* @author Estevam.Meneses
* @since 05/10/2019
* @apiNote Calculates the duration of a method execution and prints the result in milliseconds. 
* @category Annotation
* 
*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TimerExecution {

}
