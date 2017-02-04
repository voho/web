# EC2 Instance Preparation

OS: Amazon Linux

## Install Tomcat

    sudo yum install tomcat8

## Install CodeDeploy Agent

    cd /home/ec2-user
    wget https://aws-codedeploy-eu-west-1.s3.amazonaws.com/latest/install
    chmod +x ./install
    sudo ./install auto
    sudo service codedeploy-agent status