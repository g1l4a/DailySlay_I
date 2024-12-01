.class public MyProgram
.super java/lang/Object
.method public static main([Ljava/lang/String;)V
   .limit stack 10
   .limit locals 10
   ldc 1
   iload_0
   ldc 1
   if_icmpgt L2_3
   getstatic java/lang/System/out Ljava/io/PrintStream;
   iload_0
   invokevirtual java/io/PrintStream/println(I)V
   iinc 2 1
   goto L0_1
   L2_3:
   ldc 5
   iload_0
   ldc 5
   if_icmpgt L6_7
   getstatic java/lang/System/out Ljava/io/PrintStream;
   iload_0
   invokevirtual java/io/PrintStream/println(I)V
   iinc 5 1
   goto L4_5
   L6_7:
   return
.end method
