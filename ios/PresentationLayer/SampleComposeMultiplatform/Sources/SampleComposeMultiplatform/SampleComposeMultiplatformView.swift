//
//  Created by Julia Jakubcova on 02/08/2024
//  Copyright © 2024 Matee. All rights reserved.
//

import DependencyInjection
import Factory
import KMPShared
import SwiftUI
import UIToolkit

struct SampleComposeMultiplatformView: View {

    private weak var flowController: FlowController?
    
    @State private var toastData: ToastData?
    
    init(flowController: FlowController?) {
        self.flowController = flowController
    }
    
    var body: some View {
        ComposeViewController {
            SampleComposeMultiplatformScreenViewController(
                onEvent: { event in
                    switch onEnum(of: event) {
                    case .showMessage(let message):
                        toastData = ToastData(message.message, hideAfter: 2)
                    case .goToNext:
                        flowController?.handleFlow(SampleComposeMultiplatformFlow.next)
                    }
                },
                factory: SwiftUISampleComposeMultiplatformViewFactory()
            )
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .toastView($toastData)
        .navigationTitle(MR.strings().bottom_bar_item_3.toLocalized())
    }
}
