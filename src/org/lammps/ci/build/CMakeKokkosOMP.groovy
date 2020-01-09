package org.lammps.ci.build

class CMakeKokkosOMP extends CMakeBuild {
    CMakeKokkosOMP(steps) {
        super("jenkins/cmake/kokkos-omp", steps)
        cmake_options = ['-D CXX_COMPILER_LAUNCHER=ccache',
                         '-D CMAKE_CXX_FLAGS="-Wall -Wextra -Wno-unused-result"',
                         '-D BUILD_LIB=on',
                         '-D BUILD_SHARED_LIBS=on',
                         '-D BUILD_OMP=on',
                         '-D LAMMPS_SIZES=bigbig',
                         '-D PKG_ASPHERE=yes',
                         '-D PKG_BODY=yes=yes',
                         '-D PKG_CLASS2=yes',
                         '-D PKG_COLLOID=yes',
                         '-D PKG_COMPRESS=yes',
                         '-D PKG_CORESHELL=yes',
                         '-D PKG_DIPOLE=yes',
                         '-D PKG_GRANULAR=yes',
                         '-D PKG_KSPACE=yes',
                         '-D PKG_MANYBODY=yes',
                         '-D PKG_MC=yes',
                         '-D PKG_MISC=yes',
                         '-D PKG_MOLECULE=yes',
                         '-D PKG_MPIIO=yes',
                         '-D PKG_OPT=yes',
                         '-D PKG_PERI=yes',
                         '-D PKG_POEMS=yes',
                         '-D PKG_PYTHON=yes',
                         '-D PKG_QEQ=yes',
                         '-D PKG_REPLICA=yes',
                         '-D PKG_RIGID=yes',
                         '-D PKG_SHOCK=yes',
                         '-D PKG_SNAP=yes',
                         '-D PKG_SRD=yes',
                         '-D PKG_VORONOI=yes',
                         '-D DOWNLOAD_VORO=yes',
                         '-D PKG_USER-AWPMD=yes',
                         '-D PKG_USER-COLVARS=yes',
                         '-D PKG_USER-DIFFRACTION=yes',
                         '-D PKG_USER-DPD=yes',
                         '-D PKG_USER-DRUDE=yes',
                         '-D PKG_USER-EFF=yes',
                         '-D PKG_USER-FEP=yes',
                         '-D PKG_USER-LB=yes',
                         '-D PKG_USER-MISC=yes',
                         '-D PKG_USER-MOLFILE=yes',
                         '-D PKG_USER-PHONON=yes',
                         '-D PKG_USER-QTB=yes',
                         '-D PKG_USER-MEAMC=yes',
                         '-D PKG_USER-REAXC=yes',
                         '-D PKG_USER-SPH=yes',
                         '-D PKG_USER-TALLY=yes',
                         '-D PKG_USER-SMTBQ=yes',
                         '-D PKG_KOKKOS=yes',
                         '-D KOKKOS_ENABLE_OPENMP=yes']
    }
}
