package com.erica.gamsung.learning

import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ExampleFunSpecTest :
    FunSpec({
        test("String의 length는 문자열의 길이를 반환한다") {
            "sammy".length shouldBe 5
            "".length shouldBe 0
        }

        context("context이면 블럭은 활성화된다") {
            xtest("xtest이면 이 테스트는 비활성화된다") {
                "sammy".length shouldBe 5
            }
        }

        xcontext("xcontext이면 블럭은 비활성화된다") {
            test("부모 블럭이 비활성화되면 자식 블럭은 활성화되지 않는다") {
                "".length shouldBe 0
            }
        }
    })

class ExampleStringSpecTest :
    StringSpec({
        "코틀린에서 `a to b` 문법으로 Pair를 만들 수 있다" {
            "a" to "b" shouldBe Pair("a", "b")
        }
    })
