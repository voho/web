# EC2 INSTANCE PREPARATION

sudo yum install tomcat8

cd /home/ec2-user
wget https://aws-codedeploy-eu-west-1.s3.amazonaws.com/latest/install
chmod +x ./install
sudo ./install auto
sudo service codedeploy-agent status