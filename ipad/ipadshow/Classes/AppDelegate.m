//
//  AppDelegate.m
//  ipadshow
//
//  Created by shen apple on 11-12-28.
//  Copyright __MyCompanyName__ 2011. All rights reserved.
//

#import "AppDelegate.h"
#ifdef PHONEGAP_FRAMEWORK
	#import <PhoneGap/PhoneGapViewController.h>
#else
	#import "PhoneGapViewController.h"
#endif

@implementation AppDelegate

@synthesize invokeString;

- (id) init
{	
	/** If you need to do any extra app-specific initialization, you can do it here
	 *  -jm
	 **/
    return [super init];
}

/**
 * This is main kick off after the app inits, the views and Settings are setup here. (preferred - iOS4 and up)
 */
- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
	
	NSArray *keyArray = [launchOptions allKeys];
	if ([launchOptions objectForKey:[keyArray objectAtIndex:0]]!=nil) 
	{
		NSURL *url = [launchOptions objectForKey:[keyArray objectAtIndex:0]];
		self.invokeString = [url absoluteString];
		NSLog(@"ipadshow launchOptions = %@",url);
		NSLog(@"ipadshow absoluteString = %@",self.invokeString);
	}else{
		NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
		NSString *basePath = ([paths count] > 0) ? [paths objectAtIndex:0] : nil;
		NSLog(@"ipadshow error = %@",basePath);
	}
	
	return [super application:application didFinishLaunchingWithOptions:launchOptions];
}

// this happens while we are running ( in the background, or from within our own app )
// only valid if ipadshow.plist specifies a protocol to handle
- (BOOL)application:(UIApplication *)application handleOpenURL:(NSURL *)url 
{
    // must call super so all plugins will get the notification, and their handlers will be called 
	// super also calls into javascript global function 'handleOpenURL'
	
	if (!url) {  return NO; }

	NSLog(@"handleOpenURL = %@",url);
	
    return [super application:application handleOpenURL:url];
}

-(id) getCommandInstance:(NSString*)className
{
	/** You can catch your own commands here, if you wanted to extend the gap: protocol, or add your
	 *  own app specific protocol to it. -jm
	 **/
	return [super getCommandInstance:className];
}

/**
 Called when the webview finishes loading.  This stops the activity view and closes the imageview
 */
- (void)webViewDidFinishLoad:(UIWebView *)theWebView 
{
	NSLog(@"webViewDidFinishLoad = %@",self.invokeString);
	[[ActivityView sharedActivityView] hide: YES]; 

	// only valid if ipadshow.plist specifies a protocol to handle
	if(self.invokeString)
	{
		// this is passed before the deviceready event is fired, so you can access it in js when you receive deviceready
		NSString* jsString = [NSString stringWithFormat:@"var invokeString = \"%@\";", self.invokeString];
		[theWebView stringByEvaluatingJavaScriptFromString:jsString];
	}
	return [ super webViewDidFinishLoad:theWebView ];
}

- (void)webViewDidStartLoad:(UIWebView *)theWebView 
{
	NSLog(@"webViewDidStartLoad = %@",[[theWebView.request URL] path]);
	
	if ([[theWebView.request URL] path]!=NULL) {
		[[ActivityView sharedActivityView] showWithAnimate: YES];
	}
	
	//[super javascriptAlert:@"test"];
	return [ super webViewDidStartLoad:theWebView ];
}

/**
 * Fail Loading With Error
 * Error - If the webpage failed to load display an error with the reason.
 */
- (void)webView:(UIWebView *)theWebView didFailLoadWithError:(NSError *)error 
{
	NSLog(@"didFailLoadWithError = %@",error);
	if (error != NULL) {
		NSLog(@"web access error = %@",[error localizedDescription]);
		//[super javascriptAlert:[error localizedDescription]];
	}
	return [ super webView:theWebView didFailLoadWithError:error ];
}

/**
 * Start Loading Request
 * This is where most of the magic happens... We take the request(s) and process the response.
 * From here we can re direct links and other protocalls to different internal methods.
 */
- (BOOL)webView:(UIWebView *)theWebView shouldStartLoadWithRequest:(NSURLRequest *)request navigationType:(UIWebViewNavigationType)navigationType
{
	NSLog(@"shouldStartLoadWithRequest = %@",self.invokeString);
	return [ super webView:theWebView shouldStartLoadWithRequest:request navigationType:navigationType ];
}


- (BOOL) execute:(InvokedUrlCommand*)command
{
	NSLog(@"execute = %@",self.invokeString);
	return [ super execute:command];
}




- (void)dealloc
{
	[ super dealloc ];
}

@end
