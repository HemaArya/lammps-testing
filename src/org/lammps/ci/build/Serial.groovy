package org.lammps.ci.build

name = 'jenkins/serial'

def build() {
    env.CCACHE_DIR= pwd() + '/.ccache'
    env.COMP     = 'g++'
    env.MACH     = 'serial'
    env.LMPFLAGS = '-sf off'
    env.LMP_INC  = '-I../../src/STUBS -I/usr/include/hdf5/serial -DLAMMPS_SMALLSMALL -DFFT_KISSFFT -DLAMMPS_GZIP -DLAMMPS_PNG -DLAMMPS_JPEG -Wall -Wextra -Wno-unused-result -Wno-unused-parameter -Wno-maybe-uninitialized'
    env.JPG_LIB  = '-L../../src/STUBS/ -L/usr/lib/x86_64-linux-gnu/hdf5/serial/ -lmpi_stubs -ljpeg -lpng -lz'

    env.CC = 'gcc'
    env.CXX = 'g++'
    env.OMPI_CC = 'gcc'
    env.OMPI_CXX = 'g++'

    sh 'ccache -C'
    sh 'ccache -M 5G'

    // clean up project directory
    stage('Enabling modules') {
        sh '''
        make -C src purge
        make -C src clean-all
        make -C src yes-all
        make -C src no-lib
        make -C src no-mpiio
        make -C src no-user-omp
        make -C src no-user-intel
        make -C src no-user-lb
        make -C src no-user-smd
        make -C src yes-user-molfile yes-compress yes-python
        make -C src yes-poems yes-user-colvars yes-user-awpmd yes-user-meamc
        make -C src yes-user-h5md
        '''
    }

    stage('Building libraries') {
        sh '''
        make -C lib/colvars -f Makefile.g++ clean
        make -C lib/poems -f Makefile.g++ CXX="${COMP}" clean
        make -C lib/awpmd -f Makefile.mpicc CC="${COMP}" clean
        make -C lib/h5md -f Makefile.h5cc clean
        make -C src/STUBS clean

        make -j 8 -C lib/colvars -f Makefile.g++ CXX="${COMP}"
        make -j 8 -C lib/poems -f Makefile.g++ CXX="${COMP}"
        make -j 8 -C lib/awpmd -f Makefile.mpicc CC="${COMP}"
        make -j 8 -C lib/h5md -f Makefile.h5cc
        '''
    }

    stage('Compiling') {
        sh 'make -j 8 -C src ${MACH} MPICMD="${MPICMD}" CC="${COMP}" LINK="${COMP}" LMP_INC="${LMP_INC}" JPG_LIB="${JPG_LIB}" TAG="${TAG}-$CC" LMPFLAGS="${LMPFLAGS}"'
    }

    sh 'ccache -s'
}

return this
