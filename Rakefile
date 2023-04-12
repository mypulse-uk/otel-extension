require 'git'
require 'rake_github'
require 'rake_gpg'
require 'rake_ssh'
require 'rake_fly'
require 'yaml'
require 'confidante'
require 'rake_terraform'

require_relative 'lib/version'

configuration = Confidante.configuration
version = Version.from_file('build/version')

task :default => [:'library:check', :'test:all']

RakeFly.define_installation_tasks(version: '7.7.0')
RakeTerraform.define_installation_tasks(
  path: File.join(Dir.pwd, 'vendor', 'terraform'),
  version: '1.0.10')

task :version do
  puts version.to_s
end

namespace :library do
  task :build do
    sh('./gradlew build')
  end
end

namespace :ci do
  RakeFly.define_project_tasks(
    pipeline: 'otel-extension',
    argument_names: [:deployment_type, :deployment_label],
    backend: RakeFly::Tasks::Authentication::Login::FlyBackend
  ) do |t, args|
    configuration = configuration
                      .for_scope(args.to_h.merge(role: 'pipeline'))

    t.concourse_url = configuration.ci_server_url
    t.config = "pipelines/pipeline.yaml"
    t.non_interactive = true
    t.vars = configuration.vars
    t.home_directory = 'build/fly'
  end
end
def git_sha
  repo.object('HEAD').sha
end

namespace :bootstrap do
  RakeTerraform.define_command_tasks(
    configuration_name: 'bootstrap',
    argument_names: [:deployment_type, :deployment_label]
  ) do |t, args|
    configuration = configuration
                      .for_scope(args.to_h.merge(role: 'bootstrap'))

    deployment_identifier = configuration.deployment_identifier
    vars = configuration.vars

    t.source_directory = 'infra/bootstrap'
    t.work_directory = 'build'

    t.state_file =
      File.join(
        Dir.pwd, "state/bootstrap/#{deployment_identifier}.tfstate")
    t.vars = vars
  end
end
