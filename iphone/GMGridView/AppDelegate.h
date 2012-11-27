//
//  AppDelegate.h
//  GMGridView
//
//  Created by Gulam Moledina on 11-10-09.
//  Copyright (c) 2011 GMoledina.ca. All rights reserved.
//

#import <UIKit/UIKit.h>


@class ViewController;

@interface AppDelegate : UIResponder <UIApplicationDelegate>{
    
    UIImageView *zView;//Z图片ImageView
    UIImageView *fView;//F图片ImageView
    
    
    UIView *rView;//图片的UIView
    UIProgressView *progressView;

}


@property (strong, nonatomic) UIWindow *window;

@property (strong, nonatomic) ViewController *viewController;

@end
