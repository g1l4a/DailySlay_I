.source FooMain.j
.class public FooMain
.super java/lang/Object

.method public static main([Ljava/lang/String;)V
    .limit stack 2
    .limit locals 2

    ; Declare and initialize variable b to 6
    ldc 6
    istore_1

    ; Call foo with b as the argument
    iload_1
    invokestatic FooMain/foo(I)V
 
    return
.end method

.method public static foo(I)V
    .limit stack 2
    .limit locals 1

    ; Print the value of the integer argument a
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload_0
    invokevirtual java/io/PrintStream/println()V

    return
.end method
