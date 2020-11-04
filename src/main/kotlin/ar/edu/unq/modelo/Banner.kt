package ar.edu.unq.modelo

public class Banner() {
    var id: Int = 0
    var image: String = ""
    var category: String = ""

    constructor(image: String, category: String) {
        this.id++
        this.image = image
        this.category = category
    }
}