#!/bin/bash -x
exists()
{
  command -v "$1" >/dev/null 2>&1
}

if exists "cmake3"
then
    CMAKE_COMMAND=cmake3
else
    CMAKE_COMMAND=cmake
fi

LAMMPS_COMPILE_NPROC=8
LAMMPS_CXX_COMPILER_FLAGS="-Wall -Wextra -Wno-unused-result -Wno-maybe-uninitialized"
LAMMPS_C_COMPILER_FLAGS="-Wall -Wextra -Wno-unused-result -Wno-maybe-uninitialized"

export CCACHE_DIR="$PWD/.ccache"
export PYTHON=$(which python3)

# Set up environment
ccache -M 5G
virtualenv --python=$PYTHON pyenv
source pyenv/bin/activate

# Create build directory
if [ -d "build" ]; then
    rm -rf build
fi

mkdir -p build
cd build

# Configure
${CMAKE_COMMAND} -C ${LAMMPS_DIR}/cmake/presets/all_off.cmake \
      -D CMAKE_CXX_COMPILER_LAUNCHER=ccache \
      -D CMAKE_CXX_FLAGS="${LAMMPS_CXX_COMPILER_FLAGS}" \
      -D CMAKE_C_FLAGS="${LAMMPS_C_COMPILER_FLAGS}" \
      -D CMAKE_INSTALL_PREFIX=${VIRTUAL_ENV} \
      -D BUILD_MPI=on \
      -D BUILD_OMP=off \
      -D BUILD_SHARED_LIBS=on \
      -D LAMMPS_SIZES=SMALLBIG \
      -D LAMMPS_EXCEPTIONS=on \
      -D PKG_ASPHERE=on \
      -D PKG_BODY=on \
      -D PKG_CLASS2=on \
      -D PKG_COLLOID=on \
      -D PKG_COMPRESS=on \
      -D PKG_CORESHELL=on \
      -D PKG_DIPOLE=on \
      -D PKG_GRANULAR=on \
      -D PKG_KSPACE=on \
      -D PKG_MANYBODY=on \
      -D PKG_MC=on \
      -D PKG_MISC=on \
      -D PKG_MOLECULE=on \
      -D PKG_OPT=on \
      -D PKG_PERI=on \
      -D PKG_POEMS=on \
      -D PKG_PYTHON=on \
      -D PKG_QEQ=on \
      -D PKG_REPLICA=on \
      -D PKG_RIGID=on \
      -D PKG_SHOCK=on \
      -D PKG_SNAP=on \
      -D PKG_SPIN=on \
      -D PKG_SRD=on \
      -D PKG_USER-AWPMD=on \
      -D PKG_USER-BOCS=on \
      -D PKG_USER-CGDNA=on \
      -D PKG_USER-CGSDK=on \
      -D PKG_USER-COLVARS=on \
      -D PKG_USER-DIFFRACTION=on \
      -D PKG_USER-DPD=on \
      -D PKG_USER-DRUDE=on \
      -D PKG_USER-EFF=on \
      -D PKG_USER-FEP=on \
      -D PKG_USER-H5MD=on \
      -D PKG_USER-MANIFOLD=on \
      -D PKG_USER-MEAMC=on \
      -D PKG_USER-MESODPD=on \
      -D PKG_USER-MGPT=on \
      -D PKG_USER-MISC=on \
      -D PKG_USER-MOFFF=on \
      -D PKG_USER-MOLFILE=on \
      -D PKG_USER-PHONON=on \
      -D PKG_USER-PTM=on \
      -D PKG_USER-QTB=on \
      -D PKG_USER-REAXC=on \
      -D PKG_USER-SDPD=on \
      -D PKG_USER-SMTBQ=on \
      -D PKG_USER-SPH=on \
      -D PKG_USER-TALLY=on \
      -D PKG_USER-UEF=on \
      -D PKG_USER-YAFF=on \
      -D PKG_MPIIO=on \
      -D PKG_USER-LB=on \
      ${LAMMPS_DIR}/cmake || exit 1

# Build
make -j ${LAMMPS_COMPILE_NPROC} || exit 1

# Install
make install || exit 1
deactivate

ccache -s
