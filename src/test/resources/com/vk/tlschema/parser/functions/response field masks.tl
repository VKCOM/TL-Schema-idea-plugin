// TODO: correct, after correcting the grammar
//@read getUser
//  fields_mask:#
//  user_id:int
//  result_user_height:fields_mask.0?true
//  = (User fields_mask);

@read getUser fields_mask:#
  user_fields_mask:fields_mask.0?#
  result_user_height:user_fields_mask.0?true
  = GetUserResult fields_mask user_fields_mask;
