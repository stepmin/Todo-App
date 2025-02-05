//
//  Created by Julia Jakubcova on 11/10/2024
//  Copyright © 2024 Matee. All rights reserved.
//

import Foundation
import KMPShared

 #warning("TODO: Remove this workaround when issue [https://github.com/icerockdev/moko-resources/issues/714] is resolved")
 public func fixMokoResourcesForTests() {
    if ProcessInfo.processInfo.processName == "xctest" {
        MokoResourcesWorkaroundKt.nsBundle = Bundle(for: KotlinBase.self)
    }
 }

 #warning("TODO: Remove this workaround when issue [https://github.com/icerockdev/moko-resources/issues/714] is resolved")
 public func fixMokoResourcesForPreviews() {
    if ProcessInfo.processInfo.processName == "XCPreviewAgent" {
        MokoResourcesWorkaroundKt.nsBundle = Bundle(for: KotlinBase.self)
    }
 }
