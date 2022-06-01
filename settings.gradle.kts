rootProject.name = "better_direnv"

include(
    "modules/core",
    "modules/products/goland",
    "modules/products/idea",
    "modules/products/shellscript"
)

rootProject.children.forEach {
    it.name = (it.name.replaceFirst("modules/", "better_direnv/").replace("/", "-"))
}
