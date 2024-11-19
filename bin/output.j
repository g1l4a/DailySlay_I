.class public MyProgram
.super java/lang/Object
.method public static main([Ljava/lang/String;)V
   .limit stack 10
   .limit locals 10
   ldc 3
   newarray int
   astore_0

   aload_0
   ldc 0
   ldc 10
   iastore

   aload_0
   ldc 1
   ldc 20
   iastore
   
   getstatic java/lang/System/out Ljava/io/PrintStream;
   aload_0
   ldc 0
   iaload
   invokevirtual java/io/PrintStream/println(I)V
   return
.end method
