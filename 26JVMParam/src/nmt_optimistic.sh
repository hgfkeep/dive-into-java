javac win/hgfdodo/jvm/HelloWorld.java

java -XX:NativeMemoryTracking=summary -XX:+UnlockDiagnosticVMOptions -XX:+PrintNMTStatistics  -XX:+UseParallelGC -XX:-TieredCompilation win.hgfdodo.jvm.HelloWorld