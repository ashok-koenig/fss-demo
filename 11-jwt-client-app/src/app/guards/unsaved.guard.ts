import { CanDeactivateFn } from '@angular/router';

export const unsavedGuard: CanDeactivateFn<unknown> = (component:any, currentRoute, currentState, nextState) => {

  if(component && component.myForm && component.myForm.dirty){
    const result = confirm('You have some unsaved form data, are you sure to leave the from?')
    if(result){
      return true;
    }else{
      return false;
    }
  } 

  return true;
};
