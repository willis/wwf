//
//  ActivityView.h
//
//  Copyright 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

#define kAnimationDurationStart 2
#define kAnimationDurationEnd 1

@interface ActivityView : NSObject {
    IBOutlet UIActivityIndicatorView *loadingView;
    IBOutlet UIView *view;
    BOOL isShow;
}

@property (nonatomic, readonly) UIActivityIndicatorView *loadingView;
@property (nonatomic, readonly) UIView *view;
@property (nonatomic) BOOL isShow;

+ (ActivityView *)sharedActivityView;

- (void)showWithAnimate:(BOOL)animate;
- (void)hide:(BOOL)animate;
@end