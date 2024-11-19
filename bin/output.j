.class public MyProgram
.super java/lang/Object
.method public static main([Ljava/lang/String;)V
   .limit stack 10
   .limit locals 10
   iconst_0
istore_0
   ldc 10
   ldc 20
   ldc 0
aload myArray
iaload
   ldc 1
aload myArray
iaload
   iadd
   getstatic java/lang/System/out Ljava/io/PrintStream;
   ldc 0
aload myArray
iaload
   invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
   getstatic java/lang/System/out Ljava/io/PrintStream;
   ldc 1
aload myArray
iaload
   invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
   getstatic java/lang/System/out Ljava/io/PrintStream;
   ldc 2
aload myArray
iaload
   invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
   return
.end method
