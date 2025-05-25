println "Hello, Groovy!"
pipeline {
    agent any
    tools {
        maven 'Maven3.9.9' // 与全局配置中的名称一致
      }
    environment {
        // 设置环境变量
        VERSION = '1.0.0'
        DEPLOY_ENV = 'production'
        CREDENTIALS_ID = 'my-docker-creds'
//         PATH = 'org/example/TestExampleAPI'
    }
    options {
        timeout(time: 1, unit: 'HOURS')  // 设置超时时间
        disableConcurrentBuilds()         // 禁止并发构建
    }
    stages {
        stage('初始化') {
            steps {
                echo "开始执行流水线构建"
                echo "当前版本: ${VERSION}"
                echo "部署环境: ${DEPLOY_ENV}"
//                 echo "执行测试路径： ${PATH}"
            }
        }

        stage('代码检出') {
            steps {
                git branch: 'master',
                url: 'https://github.com/krispengshinchan/java_playwright_test.git'
            }
        }

        stage('代码构建') {
            steps {
                println('构架前')
//                 bat 'chcp 65001'
//                 bat 'C:\\Windows\\System32\\cmd.exe mvn clean package'  // Maven 项目示例
                bat """
                      echo '你好你好你好'
                      chcp 65001
                    """
                println('构架后')
                //                       mvn clean package
                // 或使用 gradle: sh './gradlew build'
                // 或使用 npm: sh 'npm install && npm run build'
            }
        }

        stage('单元测试') {
            steps {
                println('测试前')
//                 bat "mvn -v"
//                 bat "mvn test -Dtest=org/example/TestExampleAPI"
                bat """

                      mvn test -Dtest=org/example/TestExampleAPI
                    """
                println('测试中')
                // 生成测试报告
                junit '**/target/surefire-reports/*.xml'
                println('测试后')
            }
        }

//         stage('代码扫描') {
//             steps {
//                 println('扫描前')
//                 // 使用 SonarQube 进行代码分析
//                 withSonarQubeEnv('sonar-server') {
//                     sh 'mvn sonar:sonar'
//                 }
//                 println('扫描后')
//             }
//         }


    }
    post {
        always {
            echo "always清理工作空间..."
//             deleteDir() // 清理工作目录
        }
        success {
            println("成功前")
            script{
                currentBuild.description = "\n success"
            }
            println("成功后")
//             slackSend channel: '#ci-cd',
//                      message: "构建成功: ${env.JOB_NAME} ${env.BUILD_NUMBER}"
        }
        failure {
            println("失败前")
            script{
                    currentBuild.description = "\n failure"
            }
            println("失败后")
//             slackSend channel: '#ci-cd',
//                      color: 'danger',
//                      message: "构建失败: ${env.JOB_NAME} ${env.BUILD_NUMBER}"
        }
    }
}