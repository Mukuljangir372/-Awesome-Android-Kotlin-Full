package com.mu.jan.problems

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class MyAnnotation {

}
class CustomAnnotation {

    @MyAnnotation
    public fun foo(){

    }
}