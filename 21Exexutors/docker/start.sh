docker run -it -m 300m --cap-add=SYS_PTRACE -e jAVA_OPTS="-Xmx1m -XX:NativeMemoryTracking=detail" -e JAVA_ARGS="100000" thread bash

# java -Xmx1m -XX:NativeMemoryTracking=detail -jar app.jar 1000000

#java -Xmx10m -XX:NativeMemoryTracking=detail -jar app.jar 1000000