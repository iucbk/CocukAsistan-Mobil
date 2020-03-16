package com.iucbk.cocuk_asistan.enums


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 16.03.2020 - 14:43          │
//└─────────────────────────────┘

enum class RegisterInputs {
    PASSWORD {
        override fun toString(): String = "Şifreniz en az 6 karekterden olusmalıdır"
    },
    EMAIL {
        override fun toString(): String = "Geçerli bir email giriniz"
    },
    USERNAME {
        override fun toString(): String = "İsminiz en az 2 karekter olmalıdır"
    }
}