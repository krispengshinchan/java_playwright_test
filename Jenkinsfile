println "Hello, Groovy!"
pipeline {
    agent any
    tools {
        maven 'Maven3.9.9' // ��ȫ�������е�����һ��
      }
    environment {
        // ���û�������
        VERSION = '1.0.0'
        DEPLOY_ENV = 'production'
        CREDENTIALS_ID = 'my-docker-creds'
        PATH = 'org/example/TestExampleAPI'
    }
    options {
        timeout(time: 1, unit: 'HOURS')  // ���ó�ʱʱ��
        disableConcurrentBuilds()         // ��ֹ��������
    }
    stages {
        stage('��ʼ��') {
            steps {
                echo "��ʼִ����ˮ�߹���"
                echo "��ǰ�汾: ${VERSION}"
                echo "���𻷾�: ${DEPLOY_ENV}"
                echo "ִ�в���·���� ${PATH}"
            }
        }

        stage('������') {
            steps {
                git branch: 'master',
                url: 'https://github.com/krispengshinchan/java_playwright_test.git'
            }
        }

        stage('���빹��') {
            steps {
                println('����ǰ')
                bat 'mvn clean package'  // Maven ��Ŀʾ��
                println('���ܺ�')
                // ��ʹ�� gradle: sh './gradlew build'
                // ��ʹ�� npm: sh 'npm install && npm run build'
            }
        }

        stage('��Ԫ����') {
            steps {
                println('����ǰ')
//                 bat "mvn -v"
//                 bat "mvn test -Dtest=org/example/TestExampleAPI"
                println('������')
                // ���ɲ��Ա���
                junit '**/target/surefire-reports/*.xml'
                println('���Ժ�')
            }
        }

        stage('����ɨ��') {
            steps {
                println('ɨ��ǰ')
                // ʹ�� SonarQube ���д������
                withSonarQubeEnv('sonar-server') {
                    sh 'mvn sonar:sonar'
                }
                println('ɨ���')
            }
        }


    }
    post {
        always {
            echo "always�������ռ�..."
//             deleteDir() // ������Ŀ¼
        }
        success {
            println("�ɹ�ǰ")
            script{
                currentBuild.description = "\n success"
            }
            println("�ɹ���")
//             slackSend channel: '#ci-cd',
//                      message: "�����ɹ�: ${env.JOB_NAME} ${env.BUILD_NUMBER}"
        }
        failure {
            println("ʧ��ǰ")
            script{
                    currentBuild.description = "\n failure"
            }
            println("ʧ�ܺ�")
//             slackSend channel: '#ci-cd',
//                      color: 'danger',
//                      message: "����ʧ��: ${env.JOB_NAME} ${env.BUILD_NUMBER}"
        }
    }
}