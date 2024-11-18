.class public MyProgram
.super java/lang/Object
.method public static foo(I)V
   .limit stack 10
   .limit locals 3
   getstatic java/lang/System/out Ljava/io/PrintStream;
   iload_0
   invokevirtual java/io/PrintStream/println(I)V
   return
.end method
.method public static main([Ljava/lang/String;)V
   .limit stack 10
   .limit locals 2
   ldc 6
   istore_1
   iload_1
   invokestatic MyProgram/foo(I)V
   return
.end method
