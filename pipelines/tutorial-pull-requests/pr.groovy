@Library('lammps_testing')
import org.lammps.ci.TutorialBuild

node('atlas2') {
    def build = new TutorialBuild()
    build.pull_request(env.JOB_BASE_NAME)
}
