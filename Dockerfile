FROM ubuntu:22.04

# Install dependencies
RUN apt-get update && \
    apt-get install -y curl unzip wget gnupg2 software-properties-common

# Install Java
RUN add-apt-repository ppa:openjdk-r/ppa && \
    apt-get update && \
    apt-get install -y openjdk-8-jdk

# Install Gradle
ENV GRADLE_VERSION=7.4
RUN wget -q https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-bin.zip && \
    unzip gradle-$GRADLE_VERSION-bin.zip && \
    rm gradle-$GRADLE_VERSION-bin.zip && \
    mv gradle-$GRADLE_VERSION /opt/gradle && \
    ln -s /opt/gradle/bin/gradle /usr/bin/gradle

# Set environment variables
ENV JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
ENV GRADLE_HOME=/opt/gradle

# Install Google Chrome
RUN wget -q -O - https://dl.google.com/linux/linux_signing_key.pub \
      | gpg --dearmor > /usr/share/keyrings/google-chrome.gpg && \
    echo "deb [arch=amd64 signed-by=/usr/share/keyrings/google-chrome.gpg] \
      http://dl.google.com/linux/chrome/deb/ stable main" \
      > /etc/apt/sources.list.d/google-chrome.list && \
    apt-get update && \
    apt-get install -y google-chrome-stable

# Detect Chrome version, attempt major‐match, then fallback, install ChromeDriver
RUN set -eux; \
    CHROME_BIN="$(which google-chrome || which google-chrome-stable)"; \
    CHROME_VERSION="$($CHROME_BIN --version \
        | sed -E 's/.* ([0-9]+\.[0-9]+\.[0-9]+).*/\1/')"; \
    CHROME_MAJOR="${CHROME_VERSION%%.*}"; \
    echo "→ Chrome version: ${CHROME_VERSION}"; \
    echo "→ Chrome major:   ${CHROME_MAJOR}"; \
    if DRIVER_VERSION=$(curl -fsS \
         "https://chromedriver.storage.googleapis.com/LATEST_RELEASE_${CHROME_MAJOR}"); then \
        echo "→ Matched ChromeDriver: ${DRIVER_VERSION}"; \
    else \
        DRIVER_VERSION=$(curl -fsS \
         https://chromedriver.storage.googleapis.com/LATEST_RELEASE); \
        echo "→ Falling back to latest ChromeDriver: ${DRIVER_VERSION}"; \
    fi; \
    curl -fsS -o /tmp/chromedriver.zip \
        "https://chromedriver.storage.googleapis.com/${DRIVER_VERSION}/chromedriver_linux64.zip"; \
    unzip /tmp/chromedriver.zip -d /usr/local/bin; \
    chmod +x /usr/local/bin/chromedriver; \
    rm /tmp/chromedriver.zip

# Add Gradle and Java to PATH
ENV PATH=$PATH:$GRADLE_HOME/bin:$JAVA_HOME/bin

RUN apt-get update && apt-get install -y sudo

RUN username="selenium" && \
    addgroup -gid 1000 $username && \
    mkdir -p "/home/$username" && \
    cp -a /root/. "/home/$username" && \
    adduser --uid 1000 --home "/home/$username" --gid 1000 --quiet --disabled-password --gecos "Mr. $username User,,,"  $username && \
    usermod -p "Q4oQmhJG0ctkM" $username && \
    sudo usermod -a -G sudo $username && \
    chown -R "$username.$username" "/home/$username"

ENV TZ=Europe/Budapest
ENV DEBIAN_FRONTEND=noninteractive
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /home/selenium/
ENV HOME=/home/selenium/

USER selenium

CMD ["/bin/bash"]