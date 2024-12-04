.class public MyProgram
.super java/lang/Object
.method public static main([Ljava/lang/String;)V
   .limit stack 10
   .limit locals 10
   ldc 10
   istore_0
   ldc 20
   istore_1
   iload_0
   iload_1
   iadd
   ldc 2
   iload_0
   iload_1
   isub
   ldc 2
   idiv
   iadd
   imul
   istore_2
   getstatic java/lang/System/out Ljava/io/PrintStream;
   iload_0
   invokevirtual java/io/PrintStream/println(I)V
   getstatic java/lang/System/out Ljava/io/PrintStream;
   iload_1
   invokevirtual java/io/PrintStream/println(I)V
   getstatic java/lang/System/out Ljava/io/PrintStream;
   iload_2
   invokevirtual java/io/PrintStream/println(I)V
   return
.end method
