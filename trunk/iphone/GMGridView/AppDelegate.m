//
//  AppDelegate.m
//  GMGridView
//
//  Created by Gulam Moledina on 11-10-09.
//  Copyright (c) 2011 GMoledina.ca. All rights reserved.
//

#import "AppDelegate.h"
//#import "Demo1ViewController.h"
#import "Demo2ViewController.h"
#import "MainTableViewController.h"

#import <QuartzCore/QuartzCore.h>
#import "AFNetWorking.h"

@implementation AppDelegate

@synthesize window = _window;


- (void)TheAnimation{
    
    CATransition *animation = [CATransition animation];
    animation.delegate = self;
    animation.duration = 0.7 ;  // 动画持续时间(秒)
    animation.timingFunction = UIViewAnimationCurveEaseInOut;
    animation.type = kCATransitionFade;//淡入淡出效果
    
    NSUInteger f = [[rView subviews] indexOfObject:fView];
    NSUInteger z = [[rView subviews] indexOfObject:zView];
    [rView exchangeSubviewAtIndex:z withSubviewAtIndex:f];
    
    [[rView layer] addAnimation:animation forKey:@"animation"];
    
    [self performSelector:@selector(ToUpSide) withObject:nil afterDelay:2];//2秒后执行TheAnimation
}

#pragma mark - 上升效果
- (void)ToUpSide {
    
    [self moveToUpSide];//向上拉界面
    
}

- (void)moveToUpSide {
    [UIView animateWithDuration:0.7 //速度0.7秒
                     animations:^{//修改rView坐标
                         rView.frame = CGRectMake(self.window.frame.origin.x,
                                                  -self.window.frame.size.height,
                                                  self.window.frame.size.width,
                                                  self.window.frame.size.height);
                     }
                     completion:^(BOOL finished){
                     }];
    
}

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    // Override point for customization after application launch.
    
    //MainTableViewController *viewController = [[MainTableViewController alloc] init];
    UITabBarController *tabBarController = [[UITabBarController alloc] init];
    
    [tabBarController setViewControllers:[NSArray arrayWithObjects:[[UINavigationController alloc] initWithRootViewController:[[MainTableViewController alloc] init]],[[UINavigationController alloc] initWithRootViewController:[[Demo2ViewController alloc] init]],nil]];
    
    UITabBarItem* item0 = [tabBarController.tabBar.items objectAtIndex:0];
    [item0 setFinishedSelectedImage:[UIImage imageNamed:@"preferences-system-2"] withFinishedUnselectedImage:[UIImage imageNamed:@"preferences-system-3"]];
    
    UITabBarItem* item1 = [tabBarController.tabBar.items objectAtIndex:1];
    [item1 setFinishedSelectedImage:[UIImage imageNamed:@"video-display-2"] withFinishedUnselectedImage:[UIImage imageNamed:@"video-display-3"]];
    
    self.window.rootViewController = tabBarController;
    [self.window makeKeyAndVisible];
    
    /*
    //NSURL *url = [NSURL URLWithString:@"https://alpha-api.app.net/stream/0/posts/stream/global"];
    //NSURL *url = [NSURL URLWithString:@"http://me.vivame.cn/pcweb/template/home/data.ajax?pageNum=1"];
    NSURL *url = [NSURL URLWithString:@"http://192.168.0.43:9091/pcweb/template/home/data.ajax?pageNum=1"];
    NSURLRequest *request = [NSURLRequest requestWithURL:url];
    AFJSONRequestOperation *operation = [AFJSONRequestOperation JSONRequestOperationWithRequest:request success:^(NSURLRequest *request, NSHTTPURLResponse *response, id JSON) {
        //NSLog(@"JSON %@",JSON);
        UITabBarController *tabBarController = [[UITabBarController alloc] init];
        [tabBarController setViewControllers:[NSArray arrayWithObjects:
                                              [[UINavigationController alloc] initWithRootViewController:[[Demo1ViewController alloc] initWithJSON:JSON]],
                                              [[UINavigationController alloc] initWithRootViewController:[[Demo2ViewController alloc] init]],
                                              nil]];
        
        UITabBarItem* item0 = [tabBarController.tabBar.items objectAtIndex:0];
        [item0 setFinishedSelectedImage:[UIImage imageNamed:@"preferences-system-2"] withFinishedUnselectedImage:[UIImage imageNamed:@"preferences-system-3"]];
        
        UITabBarItem* item1 = [tabBarController.tabBar.items objectAtIndex:1];
        [item1 setFinishedSelectedImage:[UIImage imageNamed:@"video-display-2"] withFinishedUnselectedImage:[UIImage imageNamed:@"video-display-3"]];
        
        self.window.rootViewController = tabBarController;
        [self moveToUpSide];
    } failure:^(NSURLRequest *request, NSHTTPURLResponse *response, NSError *error, id JSON){
        NSLog(@"ERROR JSON %@",error);

        UIAlertView *myAlert = [[UIAlertView alloc] initWithTitle:@""
                                                          message:@"服务器正在维护中，请稍后再试。"
                                                         delegate:self
                                                cancelButtonTitle:@"退出"
                                                otherButtonTitles:nil];
        
        //progressView = [[UIProgressView alloc] initWithProgressViewStyle:UIProgressViewStyleBar];
        //progressView.frame = CGRectMake(30, 20, 225, 30);
        //[myAlert addSubview:progressView];
        
        
        //[myAlert addButtonWithTitle:@"重试"];
        
        [myAlert show];
        
        //[[myAlert subviews] makeObjectsPerformSelector:@selector(removeFromSuperview)];
    }];
    [operation start];
    
    //test gcd
    dispatch_async(dispatch_get_global_queue(0, 0), ^{
        NSLog(@"dispatch_get_global_queue");
        dispatch_async(dispatch_get_main_queue(), ^{NSLog(@"dispatch_async");});
    });
    
    [self.window makeKeyAndVisible];
     */
    
    fView =[[UIImageView alloc]initWithFrame:self.window.frame];//初始化fView
    fView.image=[UIImage imageNamed:@"ad.png"];//图片f.png 到fView
    
    zView=[[UIImageView alloc]initWithFrame:self.window.frame];//初始化zView
    zView.image=[UIImage imageNamed:@"loading.png"];//图片z.png 到zView
    
    rView=[[UIView alloc]initWithFrame:self.window.frame];//初始化rView
    
    [rView addSubview:fView];//add 到rView
    [rView addSubview:zView];//add 到rView
    
    [self.window addSubview:rView];//add 到window

    UIView* line = [[UIView alloc] initWithFrame:CGRectMake(0, self.window.bounds.size.height - 2, 1, 2)];
    line.backgroundColor = [UIColor grayColor];
    [self.window addSubview:line];

    [UIView animateWithDuration:3 animations:^{
        CGRect full = self.window.bounds;
        full.size.height = 2;
        full.origin.y = self.window.bounds.size.height - 2;
        line.frame = full;
    } completion:^(BOOL finished) {
        [line removeFromSuperview];
        [UIView animateWithDuration:.6 animations:^{
            zView.alpha = 0;
            rView.alpha = 1;
        } completion:^(BOOL finished) {
            [self moveToUpSide];
            [zView removeFromSuperview];

        }];
    }];
    
    return YES;
}

- (void)navigationController:(UINavigationController *)navController willShowViewController:(UIViewController *)viewController animated:(BOOL)animated
{
    if ([viewController respondsToSelector:@selector(willAppearIn:)])
        [viewController performSelector:@selector(willAppearIn:) withObject:navController];
}

- (void)applicationWillResignActive:(UIApplication *)application
{

}

- (void)applicationDidEnterBackground:(UIApplication *)application
{

}

- (void)applicationWillEnterForeground:(UIApplication *)application
{

}

- (void)applicationDidBecomeActive:(UIApplication *)application
{

}

- (void)applicationWillTerminate:(UIApplication *)application
{

}

@end
