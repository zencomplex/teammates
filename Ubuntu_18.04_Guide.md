# Setting up a development environment

This is a step-by-step guide for setting up a development environment on your local machine.
Using this environment, you can contribute to the project by working on features, enhancements, bug fixes, etc.

All the instructions in this document work for Linux, OS X, and Windows, with the following pointers:
- Replace `./gradlew` to `gradlew.bat` if you are using Windows.
- All the commands are assumed to be run from the root project folder, unless specified otherwise.
- When a version is specified for any tool, install that version instead of the latest version available.

> If you encounter any problems during the setup process, please refer to our [troubleshooting guide](troubleshooting-guide.md) before posting a help request in our [issue tracker](https://github.com/TEAMMATES/teammates/issues).

## Step 1: Obtain your own copy of the repository

1. Install Git.
   1. (Optional but recommended) Install Sourcetree or other similar Git client.

1. Fork our repo at https://github.com/TEAMMATES/teammates. Clone the fork to your hard disk.

1. Add a remote name (e.g `upstream`) for your copy of the main repo. Fetch the remote-tracking branches from the main repo to keep it in sync with your copy.
   ```sh
   git remote add upstream https://github.com/TEAMMATES/teammates.git
   git fetch upstream
   ```
   **Verification:** Use the command `git branch -r` and the following lines should be part of the output:
   ```
    upstream/master
    upstream/release
    ```

1. Set your `master` branch to track the main repo's `master` branch.
   ```sh
   git checkout master
   git branch -u upstream/master
   ```

More information can be found at [this documentation](https://help.github.com/articles/fork-a-repo/).

## Step 2: Install necessary tools and languages

These tools are necessary regardless of whether you are developing front-end or back-end:

1. Install JDK 1.8.
   > first, update your package index.
   ```sh 
   sudo apt-get update
   ```
   > then, check your java version.
   ```sh
   java -version
   ```
   > you should see this:
   ```sh
   Command 'java' not found, but can be installed with:

   apt install default-jre
   apt install openjdk-11-jre-headless
   apt install openjdk-8-jre-headless
   apt install openjdk-9-jre-headless
   ```
   > install openjdk version 8 (JDK 1.8) 
   ```sh
   sudo apt install openjdk-8-jdk
   ```
   
1. Install Python 2.7.
   ```sh
   sudo apt install python-minimal
   ```

1. Install Google Cloud SDK (minimum version 222.0.0). Follow the directions given [here](https://cloud.google.com/sdk/downloads).
   Note that you *do not* need to [initialize the SDK](https://cloud.google.com/sdk/docs/initializing).
   
   >I still have no idea how this works, as I can't find install.sh in the Google Cloud SDK directory. -SW
   
   ```sh
   # Run the following command at the Google Cloud SDK directory

   # Linux/OS X
   ./install.sh --path-update true

   # Windows
   install.bat --path-update true
   ```
   If you are installing in Red Hat, CentOS, Fedora, Debian or Ubuntu, refer to the quick start of Google Cloud SDK for [Debian/Ubuntu](https://cloud.google.com/sdk/docs/quickstart-debian-ubuntu) or [Red Hat/CentOS/Fedora](https://cloud.google.com/sdk/docs/quickstart-redhat-centos) respectively.

   **Verification**: Run a `gcloud` command (e.g. `gcloud version`) in order to verify that you can access the SDK from the command line.

1. Run the following command to install App Engine Java SDK bundled with the Cloud SDK:
   ```sh
   # Linux/OS X/Windows
   gcloud -q components install app-engine-java
   
   # Red Hat/CentOS/Fedora
   sudo yum install google-cloud-sdk-app-engine-java
   
   # Debian/Ubuntu
   sudo apt-get install google-cloud-sdk-app-engine-java
   ```
   **Verification:** Run `gcloud version` and there should be an entry on `app-engine-java`.

If you want to develop front-end, you need to install the following:

1. Install Node.js (minimum version 8.9.4).
    > You can install Node.js and npm together (which     you will need to install Angular CLI).
    
    > You'll probably have to install curl, so first, check:
    
    ```sh
    curl --version
    ```
    > Install curl with
    
    ```sh
    sudo apt-get install curl
    ```
    > Then get the package:
    
    ```sh
    sudo curl -sL https://deb.nodesource.com/setup_11.x | sudo -E bash -
    ```
    > Then install it:
    
    ```sh
    sudo apt install nodejs
    ```
    
    > Verify installation by checking the version:
    
    ```sh
    node --version
    npm --version
    ```
    
1. (Optional but highly recommended) Install Angular CLI version 7 globally.
    > Make sure to run as sudo
   ```sh
   npm install -g @angular/cli@7
   ```
   **Verification:** Run `ng` and you should see a list of available Angular CLI commands.

## Step 3: Set up project-specific settings and dependencies

1. Run this command to create the main config files (these are not under revision control because their contents vary from developer to developer):
   ```sh
   ./gradlew createConfigs
   ```
   **Verification:** The file named `gradle.properties` should be added to the project root directory.
   
   > This is where I started to have problems. If you get an error about the PATH for JAVA_HOME, you want to try changing it. In ubuntu, run this command:
   ```sh
   export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
   ```
   >If you continue to have issues, you may want to try reinstalling nodejs or open-jdk-8-jdk with apt-get as follows:
   ```sh
   sudo apt-get install open-jdk-8-jdk
   ```
   > and
   ```sh
   sudo apt-get install nodejs
   ```
   > Another thing you can try is updating your package list again.
   ```sh
   sudo apt-get update
   ```

1. Modify the following config file:
    > Not sure about this step.
   * `gradle.properties`<br>
      If you want to use a JDK other than the one specified in your PATH variable, add the value to the variable `org.gradle.java.home`.

1. Run this command to download the necessary tools for front-end development (if you are going to be involved):
   ```sh
   npm install
   ```
   **Verification:** A folder named `node_modules` should be added to the project root directory.

**Q:** Can I set up the project in IDEs, e.g. Eclipse, IntelliJ?<br>
**A:** You are welcome to; the core team have been using IntelliJ to a varying degree of success, but IDE-based development (even with IntelliJ) is not actively supported/maintained by the team.

## Step 4: Start developing

If you have followed every step correctly, your development environment should be set up successfully.

Proceed to the development routine as outlined in [this document](development.md).
