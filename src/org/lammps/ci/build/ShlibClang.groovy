package org.lammps.ci.build

class ShlibClang extends Shlib {
    ShlibClang(steps) {
        super(steps)
        name = 'jenkins/shlib-clang'
        c_compiler = 'clang'
        cxx_compiler = 'clang++'
    }
}
