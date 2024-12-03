.class public MyProgram
.super java/lang/Object
.method public static main([Ljava/lang/String;)V
   .limit stack 10
   .limit locals 10
   ldc 3
   newarray int
   astore_0
   ldc 0
   istore_1
L0_1:
   iload_1
   ldc 2
   if_icmpgt L2_3
   aload_0
   iload_1
   iload_1
   ldc 10
   iadd
   iastore
   iinc 1 1
   goto L0_1
L2_3:
   getstatic java/lang/System/out Ljava/io/PrintStream;
   aload_0
   ldc 0
   iaload
   invokevirtual java/io/PrintStream/println(I)V
   getstatic java/lang/System/out Ljava/io/PrintStream;
   aload_0
   ldc 1
   iaload
   invokevirtual java/io/PrintStream/println(I)V
   getstatic java/lang/System/out Ljava/io/PrintStream;
   aload_0
   ldc 2
   iaload
   invokevirtual java/io/PrintStream/println(I)V
   return
.end method
