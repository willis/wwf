//
//  main.m
//  DraggableGridView
//
//  Created by Gulam Moledina on 11-10-09.
//  Copyright (c) 2011 GMoledina.ca. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "AppDelegate.h"

#if __IPHONE_OS_VERSION_MIN_REQUIRED
#import <UIKit/UIKit.h>

int main(int argc, char *argv[]) {
    /*
    @autoreleasepool {
        int retVal = UIApplicationMain(argc, argv, @"UIApplication", @"AppDelegate");
        return retVal;
    }
     */
    @autoreleasepool {
        return UIApplicationMain(argc, argv, nil, NSStringFromClass([AppDelegate class]));
    }
}
#else
#import <Cocoa/Cocoa.h>

int main(int argc, char *argv[]) {
    return NSApplicationMain(argc, (const char **)argv);
}
#endif