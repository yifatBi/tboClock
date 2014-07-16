class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }

//            name admin: "/admin/$cName/$action/$id?" {
//                controller = {
//                    "admin" + params.cName.capitalize()
//                }
//            }
        }
        "/"{
            controller = "dayReport"
            action = "index"
        }

       // "/"(view:"/index")
        "500"(view:'/error')
	}

}
