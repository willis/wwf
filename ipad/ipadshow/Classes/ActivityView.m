//
//  ActivityView.m
//
//  Copyright 2010  All rights reserved.
//

#import "ActivityView.h"

@implementation ActivityView

@synthesize loadingView, view, isShow;
//单例模式
static ActivityView *activityView;


- (id) init {
    self = [super init];
    if (self != nil) {
		
        //[[NSBundle mainBundle] loadNibNamed:@"ActivityView" owner:self options:nil];
		
		loadingView = [[UIActivityIndicatorView alloc]initWithActivityIndicatorStyle:
		 UIActivityIndicatorViewStyleGray];
		
		
		view = [[UIView alloc] initWithFrame:[[UIScreen mainScreen] applicationFrame]];
		//view = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 768, 1024)];
		loadingView.center=CGPointMake(view.center.x,view.center.y);
		[view setTag:103];
		//[view setBackgroundColor:[UIColor whiteColor]];
		[view setAlpha:0.8];
		[view addSubview:loadingView];

		NSLog(@"***********************************************************");
		NSLog(@"windows size = %d ",1);

        [[[UIApplication sharedApplication] keyWindow] addSubview:view];
        //[view setFrame:CGRectMake(0, [UIApplication sharedApplication].statusBarFrame.size.height-view.frame.size.height, view.frame.size.width, view.frame.size.height)];
        isShow = NO;
    }
    return self;
}

+ (ActivityView *)sharedActivityView {
    if (!activityView) {
        activityView = [[ActivityView alloc]init];
    }
    return activityView;
}

- (void)showWithAnimate:(BOOL)animate {
    isShow = YES;
    [view.superview bringSubviewToFront:view];
    if ( animate ) 
    {
        [activityView.loadingView startAnimating];
        //[UIView beginAnimations:nil context:NULL];
        //[UIView setAnimationDuration:kAnimationDurationStart];
        //[UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
        //[view setFrame:CGRectMake(0, [UIApplication sharedApplication].statusBarFrame.size.height, view.frame.size.width, view.frame.size.height)];
        //[UIView commitAnimations];
    }
    else 
    {
        //[view setFrame:CGRectMake(0, [UIApplication sharedApplication].statusBarFrame.size.height, view.frame.size.width, view.frame.size.height)];
    }
	
}

- (void)hide:(BOOL)animate{
    if (animate) 
    {
		[activityView.loadingView stopAnimating];
        //[UIView beginAnimations:nil context:NULL];
        //[UIView setAnimationDuration:kAnimationDurationEnd];
        //[UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
        //[view setFrame:CGRectMake(0, [UIApplication sharedApplication].statusBarFrame.size.height-view.frame.size.height, view.frame.size.width, view.frame.size.height)];
        //[UIView commitAnimations];    
    }
    else
    {
        //[view setFrame:CGRectMake(0, [UIApplication sharedApplication].statusBarFrame.size.height-view.frame.size.height, view.frame.size.width, view.frame.size.height)];
    }
    [[UIApplication sharedApplication] keyWindow].userInteractionEnabled = YES;
    isShow = NO;
}    
@end