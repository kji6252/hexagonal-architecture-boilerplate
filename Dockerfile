FROM eclipse-temurin:21-jre
#iptables, net-tools 설치
RUN apt-get update && apt-get install -y iptables net-tools dsniff

#FROM eclipse-temurin:21-jdk

# 유틸 설치 + awscli 수동 설치
#RUN apt-get update && apt-get install -y \
#    unzip \
#    curl \
#    procps \
#    groff \
#    less \
# && apt-get clean && rm -rf /var/lib/apt/lists/* \
# && curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip" \
# && unzip awscliv2.zip \
# && ./aws/install \
# && rm -rf awscliv2.zip aws

# jcmd 동작 확인 (옵션)
#RUN jcmd -h || true

# Set working directory
WORKDIR /app

# Copy the built JAR file from the build stage to the final stage
COPY build/libs/*.jar ./app.jar

# Expose the port that the application will run on
EXPOSE 8080

# Asia Time zone 설정
ENV TZ Asia/Seoul

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
