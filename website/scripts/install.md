# EC2 Instance Preparation

OS: Amazon Linux

## Install Java JDK 8

    sudo yum install java-1.8.0
    sudo yum remove java-1.7.0-openjdk
    
## Install Tomcat

    sudo yum install tomcat8

## Install CodeDeploy Agent

    cd /home/ec2-user
    wget https://aws-codedeploy-eu-west-1.s3.amazonaws.com/latest/install
    chmod +x ./install
    sudo ./install auto
    sudo service codedeploy-agent status